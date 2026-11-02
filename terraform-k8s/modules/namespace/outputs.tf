output "namespace_name" {
  description = "The name of the Kubernetes namespace."
  value       = kubernetes_namespace_v1.this.metadata[0].name
}
