resource "kubernetes_deployment_v1" "this" {
  metadata {
    name      = var.app_name
    namespace = var.namespace_name
  }
  lifecycle {
    ignore_changes = [
      spec[0].replicas
    ]
  }
  spec {
    replicas = var.replicas
    selector {
      match_labels = {
        app = var.app_label
      }
    }
    template {
      metadata {
        labels = {
          app = var.app_label
        }
      }
      spec {
        container {
          name  = "backend"
          image = var.image
          port {
            container_port = var.container_port
          }
          readiness_probe {
            http_get {
              path = var.health_path
              port = var.container_port
            }
          }
          liveness_probe {
            http_get {
              path = "/actuator/health/liveness"
              port = var.container_port
            }
          }
          startup_probe {
            http_get {
              path = var.health_path
              port = var.container_port
            }
            failure_threshold = 30
            period_seconds    = 10
          }
          resources {
            requests = {
              cpu    = "250m"
              memory = "256Mi"
            }
            limits = {
              cpu    = "500m"
              memory = "512Mi"
            }
          }
          env_from {
            secret_ref {
              name = "table-and-taste-backend-secrets"
            }
          }
        }
      }
    }
  }
}
