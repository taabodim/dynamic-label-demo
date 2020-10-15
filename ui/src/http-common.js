import axios from 'axios';

// axios.defaults.headers.common['Authorization'] = `Bearer 213`

export const MANGO_HTTP = axios.create({
    baseURL: getMangoHttpBaseUrl(),
    headers: {
        'Accept': 'application/json,*/*',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': "*",
        'Access-Control-Allow-Methods': "DELETE, GET, OPTIONS, PATCH, POST, PUT",
        'Access-Control-Allow-Headers': "Origin, X-Requested-With, Content-Type, Accept, Authorization"
    }
});

MANGO_HTTP.interceptors.request.use(config => {
    // config.headers.Authorization = 'Basic ' + authHeader();
    return config;
});

export function getMangoHttpBaseUrl() {
    if (process.env.NODE_ENV === 'production') {
      return "http://nomadini.com/api/"
    }

    return `http://localhost:9220/api/`;
}

export function authHeader() {
    // return authorization header with basic auth credentials
    let userId = localStorage.getItem('userId');
    if (userId) {
        return userId ;
    } else {
        return -123456;
    }
}
