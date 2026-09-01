import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
})

export const getUsersApi = () => api.get('/users')

export const createUserApi = (data) => api.post('/users', data)

export const getQualMasterApi = () => api.get('/qualification-master')
