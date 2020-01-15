FROM grafana/grafana:6.5.2

RUN grafana-cli plugins install marcuscalidus-svg-panel
RUN grafana-cli plugins install grafana-worldmap-panel

COPY provisioning /etc/grafana/provisioning


