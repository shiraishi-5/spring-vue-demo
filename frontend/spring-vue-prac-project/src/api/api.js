import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
})

export const getUsersApi = ({
  page = 0,
  size = 5,
  sort = 'id',
  keyword = '',
  qualificationIds,
  filterType,
}) => {
  return api.get('/users/test', {
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
