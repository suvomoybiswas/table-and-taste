
variable "app_name" {
  description = "Name of the application."
  type        = string
}

variable "namespace_name" {
  description = "Name of the Kubernetes namespace."
  type        = string
}

variable "replicas" {
  description = "Number of application replicas."
  type        = number
  default     = 2
}
variable "service_name" {
  description = "Name of the Kubernetes Service."
  type        = string
}