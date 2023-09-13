# Docker commands
```shell
docker build -t <image-name>:<tag> <path-to-Dockerfile>

docker images


docker context ls

docker context use my-context

# Run the container in the background
docker run <options> <image-name>:<tag>
## -d as detached
docker run -d --name my-container <image-name>:<tag>

# Run the container on k8s
## 1. create deployment.yml

kubectl config use-context rancher-desktop

kubectl create -f create-redispod.yml
kubectl apply -f deployment.yml

kubectl get pods -o wide | sort

kubectl delete pod <my-pod>

kubectl scale deployment <deployment-name> --replicas=0

kubectl get services kube-dns --namespace=kube-system

kubectl cluster-info

kubectl get all

kubectl port-forward service/<service-name> <container-port>:<host-port>
# Testing curl pod
kubectl run mycurlpod --image=curlimages/curl -i --tty -- sh
$ exit
Session ended, resume using 'kubectl attach mycurlpod -c mycurlpod -i -t' command when the pod is running

# Helm
brew install helm

helm repo update

# Redis k8s deploy
helm repo add bitnami https://charts.bitnami.com/bitnami
helm install redis bitnami/redis # Default values
helm install redis bitnami/redis -f redis.yml # Deploy
helm upgrade redis bitnami/redis -f redis.yml
## Debug
brew install redis

# GitLab runner k8s deploy
helm repo add gitlab https://charts.gitlab.io
```