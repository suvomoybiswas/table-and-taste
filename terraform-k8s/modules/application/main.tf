resource "kubernetes_deployment_v1" "this" {
  metadata {
    name      = var.app_name
    namespace = var.namespace_name
  }
  spec {
    replicas = var.replicas
    selector {
      match_labels = {
        app = var.app_name
      }
    }
    template {
      metadata {
        labels = {
          app = var.app_name
        }
      }
      spec {
        container {
          name  = var.app_name
          image = var.image

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
          port {
            container_port = var.container_port
          }
          readiness_probe {
            http_get {
              path = "/"
              port = 80
            }
          }
          liveness_probe {
            http_get {
              path = "/"
              port = 80
            }
          }
          startup_probe {
            http_get {
              path = "/"
              port = 80
            }

            failure_threshold     = 30
            period_seconds        = 10
            initial_delay_seconds = 0
          }
        }
      }
    }
  }
}
