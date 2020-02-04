package org.sedi.emp.restextractor

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.workday.insights.timeseries.arima.Arima
import com.workday.insights.timeseries.arima.struct.ArimaParams
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.persistence.MeasurementRepository
import org.sedi.emp.restextractor.persistence.WellRepository
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import javax.annotation.PostConstruct
import kotlin.math.floor


@RestController
@CrossOrigin("*")
@RequestMapping("prediction")
class PredictionController(private val mapper: ObjectMapper, private val measureRepo: MeasurementRepository, private val wellRepo: WellRepository) {

    private val log = LoggerFactory.getLogger(PredictionController::class.java)
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("")

    @GetMapping("/")
    fun testDatasource(): ResponseEntity<String> {
        return ResponseEntity.ok("{\"info\":\"Datasource available\"}")
    }

    @RequestMapping("/search")
    fun search(): ResponseEntity<String> {
        return ResponseEntity.ok("{}")
    }

    @RequestMapping("/query")
    fun query(@RequestBody data: JsonNode): ResponseEntity<JsonNode> {
        log.info("Prediction Request: " + mapper.writeValueAsString(data));
        val targetWell: String = data.get("targets").get(0).get("target").asText().split(",")[0];
        val targetSensor: String = data.get("targets").get(0).get("target").asText().split(",")[1];
        val from: String = data.get("range").get("from").textValue();
        val to: String = data.get("range").get("to").textValue();
        val fromDate: LocalDateTime = LocalDateTime.ofInstant(Instant.parse(from), ZoneOffset.UTC);
        val nowDate: LocalDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        val toDate: LocalDateTime = LocalDateTime.ofInstant(Instant.parse(to), ZoneOffset.UTC);

        val measures: List<Measurement> = measureRepo.loadHistoricData(UUID.fromString(targetWell), targetSensor, nowDate.minusHours(3), nowDate);
        val histData: DoubleArray = DoubleArray(measures.count());
        val stepsSizeSeconds = DoubleArray(measures.count() - 1);
        for (n in 0 until measures.count()) {
            histData[n] = measures[n].value.toDouble();
            if (n != 0) {
                stepsSizeSeconds[n - 1] = (measures[n - 1].timestamp.epochSecond - measures[n].timestamp.epochSecond).toDouble();
            }
        }
        val secondsToPredict = toDate.toEpochSecond(ZoneOffset.UTC) - nowDate.toEpochSecond(ZoneOffset.UTC);
        val steps: Int = floor(secondsToPredict / stepsSizeSeconds.average()).toInt();
        log.info("Number of Measurement in Hist Data:" + histData.size + "Average step size(history): " + stepsSizeSeconds.average() + " secondsToPredict:" + secondsToPredict + " steps:" + steps);
        val predictions = predict(histData, steps);

        var predictionresult: Array<DoubleArray> = Array(steps) { DoubleArray(2) };
        for (n in 0 until steps) {
            predictionresult[n][0] = predictions[n];
            predictionresult[n][1] = ((nowDate.toEpochSecond(ZoneOffset.UTC) + (n * stepsSizeSeconds.average())) * 1000L).toDouble();
            //result[n][1] = fromDate.toInstant(ZoneOffset.UTC).toEpochMilli() + offset;
        }

        log.info("Predictions: " + predictions.joinToString());
        val response = constructTimeSeriesResponse(predictionresult, targetWell + "," + targetSensor);
        return ResponseEntity.ok(response);
    }

    /**
     * @param histData: the historic Data
     * @param timeslots: the number of timeseries predictions to make
     *
     * Runs an ARIMA prediction Algorithm
     * */
    fun predict(histData: DoubleArray, timeslots: Int): DoubleArray {
        //val dataArray = doubleArrayOf(2.0, 1.0, 2.0, 5.0, 2.0, 1.0, 2.0, 5.0, 2.0, 1.0, 2.0, 5.0, 2.0, 1.0, 2.0, 5.0)
        val dataArray = histData;
        // Set ARIMA model parameters.
        val p = 3
        val d = 0
        val q = 3
        val P = 1
        val D = 1
        val Q = 0
        val m = 0
        val params = ArimaParams(p, d, q, P, D, Q, m);
        val forecastSize = timeslots;

        // Obtain forecast result. The structure contains forecasted values and performance metric etc.
        val forecastResult = Arima.forecast_arima(dataArray, forecastSize, params);

        // Read forecast values
        val forecastData = forecastResult.forecast // in this example, it will return { 2 }

        // You can obtain upper- and lower-bounds of confidence intervals on forecast values.
        // By default, it computes at 95%-confidence level. This value can be adjusted in ForecastUtil.java
        val uppers = forecastResult.forecastUpperConf
        val lowers = forecastResult.forecastLowerConf

        // Finally you can read log messages.
        log.info(forecastResult.log);
        // It also provides the maximum normalized variance of the forecast values and their confidence interval.
        log.info("Confidence: " + forecastResult.maxNormalizedVariance);
        // You can also obtain the root mean-square error as validation metric.
        log.info("RMSE: " + forecastResult.rmse);
        return forecastData;
    }

    fun constructTimeSeriesResponse(data: Array<DoubleArray>, target: String): JsonNode {
        val root: ArrayNode = mapper.createArrayNode();
        val obj: ObjectNode = mapper.createObjectNode();
        obj.put("target", "prediction");
        var resultArray: ArrayNode = mapper.valueToTree(data);
        obj.putArray("datapoints").addAll(resultArray);
        root.add(obj);
        return root;
    }
}