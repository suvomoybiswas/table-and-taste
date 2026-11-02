resource "kubernetes_horizontal_pod_autoscaler_v2" "backend" {
  metadata {
    name      = var.hpa_name
    namespace = var.namespace_name
  }
  spec {
    max_replicas = var.max_replicas
    min_replicas = var.min_replicas
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = var.deployment_name
    }
    metric {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = var.target_cpu_utilization
        }
      }
    }
  }
}