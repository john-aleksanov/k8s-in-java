{{- define "my-application.labels" -}}
version: {{ .Chart.AppVersion }}
current-date: {{ now | date "2006-01-02" }}
{{- end -}}