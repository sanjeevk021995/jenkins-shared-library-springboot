{{- define "springboot-logging.name" -}}
springboot-logging
{{- end }}

{{- define "springboot-logging.fullname" -}}
{{ .Release.Name }}-springboot
{{- end }}