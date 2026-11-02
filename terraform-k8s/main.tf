terraform {
  required_providers {
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.38.0"
    }
  }
}

module "namespace" {
  source         = "./modules/namespace"
  namespace_name = var.namespace_name
}
module "application" {
  source         = "./modules/application"
  app_name       = var.app_name
  image          = "nginx:latest"
  replicas       = var.replicas
  namespace_name = var.namespace_name
  container_port = 80
}
module "service" {
  source         = "./modules/service"
  namespace_name = var.namespace_name
  app_name       = var.app_name
  service_name   = var.service_name
  service_port   = 80
  target_port    = 80
}
module "backend" {
  source         = "./modules/backend"
  app_name       = "table-and-taste-backend"
  image          = "table-and-taste-backend:local"
  namespace_name = var.namespace_name
  replicas       = 1
  container_port = 8080
  app_label      = "table-and-taste-backend"
  health_path    = "/actuator/health"
}

module "backend_service" {
  source         = "./modules/service"
  namespace_name = var.namespace_name
  app_name       = "table-and-taste-backend"
  service_name   = "backend"
  service_port   = 8080
  target_port    = 8080
}
module "hpa" {
  source                 = "./modules/hpa"
  hpa_name               = "table-and-taste-backend"
  namespace_name         = var.namespace_name
  deployment_name        = "table-and-taste-backend"
  min_replicas           = 1
  max_replicas           = 5
  target_cpu_utilization = 70
}
