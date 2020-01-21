package org.sedi.emp.restextractor

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.workday.insights.timeseries.arima.Arima
import com.workday.insights.timeseries.arima.struct.ArimaParams
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import javax.annotation.PostConstruct


@RestController
@CrossOrigin("*")
@RequestMapping("prediction")
class PredictionController(private val mapper:ObjectMapper) {
		
    private val log = LoggerFactory.getLogger(PredictionController::class.java)
	private val dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern("")

    @GetMapping("/")
    fun testDatasource(): ResponseEntity<String> {
        return ResponseEntity.ok("{\"info\":\"Datasource available\"}")
    }
	@RequestMapping("/search")
	fun search(): ResponseEntity<String> {
        return ResponseEntity.ok("{}")
    }
	@RequestMapping("/query")
	fun query(@RequestBody data: JsonNode): ResponseEntity<String> {
		log.info(mapper.writeValueAsString(data));
		val from:String = data.get("range").get("from").textValue();
		val to:String = data.get("range").get("to").textValue();
		val fromDate:LocalDateTime = LocalDateTime.ofInstant(Instant.parse(from),ZoneOffset.UTC);
		val toDate:LocalDateTime = LocalDateTime.ofInstant(Instant.parse(to),ZoneOffset.UTC);
		var minutes:Long = ChronoUnit.MINUTES.between(fromDate, toDate);
		minutes = Math.round(minutes/10.0);
		var randomValues  = LongArray(minutes.toInt()) {  Random().nextInt().toLong() }.asList();
		var result: Array<LongArray> = Array(randomValues.count()) { LongArray(2) };
		var offset:Long = 0;
		for (n in 0 until randomValues.count()){
			result[n][0] = randomValues.get(n);
			offset = offset + (10 * 60000);
			result[n][1] = fromDate.toInstant(ZoneOffset.UTC).toEpochMilli() + offset;
		}
		val root:ArrayNode = mapper.createArrayNode();
		val obj:ObjectNode = mapper.createObjectNode();
		obj.put("target","bla");
		var resultarray:ArrayNode = mapper.valueToTree(result);
		obj.putArray("datapoints").addAll(resultarray);
		root.add(obj);
        return ResponseEntity.ok(mapper.writeValueAsString(root));
    }
	@PostConstruct
	fun test(){
		val now = LocalDateTime.now(ZoneOffset.UTC);
		val result = predict(now.minusHours(6),now,1,36);
		log.info("Prediction Result:" + result.joinToString());
	}
	fun predict(from:LocalDateTime, to:LocalDateTime, well_id: Int, timeslots:Int): DoubleArray {
		val dataArray = doubleArrayOf(2.0, 1.0, 2.0, 5.0, 2.0, 1.0, 2.0, 5.0, 2.0, 1.0, 2.0, 5.0, 2.0, 1.0, 2.0, 5.0)

		// Set ARIMA model parameters.
		val p = 3
		val d = 0
		val q = 3
		val P = 1
		val D = 1
		val Q = 0
		val m = 0
		val params = ArimaParams(p, d, q, P, D, Q, m);
		val forecastSize = 1

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
}