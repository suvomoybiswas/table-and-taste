
variable "namespace_name" {
  description = "The namespace where the Service will run."
  type        = string
}

variable "app_name" {
  description = "The application label used to select Pods."
  type        = string
}
variable "service_name" {
  description = "Name of the Kubernetes Service."
  type        = string
  default     = "table-and-taste-service"
}
variable "service_port" {
  description = "Port exposed by the Kubernetes Service."
  type        = number
  default     = 80
}
variable "target_port" {
  description = "Port on the destination application container."
  type        = number
  default     = 80
}