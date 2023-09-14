# Kubectl deployment files
kubectl apply -f prod --recursive
kubectl delete -f prod --recursive

# Helm deployment files
helm upgrade redis bitnami/redis -f redis-helm.yml
