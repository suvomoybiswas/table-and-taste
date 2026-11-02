variable "app_name" {
  description = "Name of the application."
  type        = string
}

variable "image" {
  description = "Container image used by the application."
  type        = string
}

variable "replicas" {
  description = "Number of application replicas."
  type        = number
  default     = 2
}
variable "namespace_name" {
  description = "The namespace where the application will run."
  type        = string
}
variable "container_port" {
  description = "Port the application container listens on."
  type        = number
  default     = 80
}


