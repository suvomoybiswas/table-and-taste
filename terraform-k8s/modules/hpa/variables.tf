
variable "hpa_name" {
  description = "Name of the Horizontal Pod Autoscaler"
  type        = string
}

variable "namespace_name" {
  description = "Kubernetes namespace where the HPA will run"
  type        = string
}

variable "deployment_name" {
  description = "Name of the Deployment managed by the HPA"
  type        = string
}

variable "min_replicas" {
  description = "Minimum number of backend Pods"
  type        = number
}

variable "max_replicas" {
  description = "Maximum number of backend Pods"
  type        = number
}

variable "target_cpu_utilization" {
  description = "Target average CPU utilization percentage"
  type        = number
}
