import axios from "axios"

const api = axios.create({
  baseURL : "http://localhost:8080",
});

// 요청 보낼 때마다 저장된 토큰을 자동으로 Authorization 헤더에 붙여줌
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("accessToken");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;