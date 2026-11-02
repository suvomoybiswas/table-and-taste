variable "app_name" {
  description = "Name of the backend application."
  type        = string
}
variable "image" {
  description = "Container image used by the backend."
  type        = string
}
variable "namespace_name" {
  description = "Kubernetes namespace where the backend will run."
  type        = string
}
variable "replicas" {
  description = "Number of backend Pods to run."
  type        = number
  default     = 1
}
variable "container_port" {
  description = "Port on which the backend container listens."
  type        = number
  default     = 8080
}
variable "health_path" {
  description = "HTTP path used to check backend health."
  type        = string
  default     = "/actuator/health"
}
variable "app_label" {
  description = "Label used to identify backend Pods."
  type        = string
  default     = "table-and-taste-backend"
}

