import axios from 'axios'

//withCredentials:trueでクッキー送受信を許可している
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true,
})

export const getUsersApi = ({
  page = 0,
  size = 5,
  sort = 'id',
  keyword = '',
  qualificationIds,
  filterType,
}) => {
  return api.get('/users', {
    params: {
      page,
      size,
      sort,
      keyword,
      qualificationIds,
      filterType,
    },
  })
}

export const createUserApi = (data) => api.post('/users', data)

export const getUserApi = (id) => api.get(`/users/${id}`)

export const getQualMasterApi = () => api.get('/qualification-master')

export const updateUserApi = (id, data) => api.put(`/users/${id}`, data)

export const loginApi = (data) => api.post('/login', data)

export const getMeApi = () => api.get('/me')
