let axInstance

let requestBodyStructure = {}
const base_url = 'http://localhost:8080/'
// nhcjApi
const init = function () {
    if (axInstance) {
        console.error('axInstance已经初始化!')
        return
    }

    axInstance = axios.create({
        baseURL: base_url,
        //  请求超时时间
        timeout: 30000
        // 跨站点访问控制请求 如果true  Access-Control-Allow-Origin 字段必须指定域名，不能为*
        // withCredentials: false,
        // responseType: 'json'
    })

    // 请求拦截器
    axInstance.interceptors.request.use(
        (config) => {
            // json请求data包结构
            if (config.headers['Content-Type'] === 'application/json' && requestBodyStructure) {
                requestBodyStructure = config.data
                config.data = JSON.stringify(requestBodyStructure)
            }
            config.headers['Authorization'] = 'bearer c8f9e161-1801-4d8c-af9a-f6cbdff5102a'
            return config
        },
        (err) => {
            return Promise.reject(err)
        }
    )

    // 响应拦截器
    axInstance.interceptors.response.use(
        (response) => {
            // 在一个ajax响应后再执行一下取消操做，把已经完成的请求从pending中移除
            if (response.config.responseType === 'blob') {
                return Promise.resolve(response.data)
            }

            let bizRes = response.data

            // 业务异常判断(根据自己业务情况做调整)
            switch (bizRes.code) {
                case 200:
                    return Promise.resolve(bizRes)
                default:
                    alert(bizRes.msg || '未知错误')
                    return Promise.reject(bizRes)
            }
        },
        (error) => {
            let messages = {}

            if (error.message === undefined) {
                return { data: {} }
            }
            if (!error.response) {
                messages.content = '网络错误，请求超时'
                messages.state = error.code
            } else {
                switch (error.response.status) {
                    case 500:
                        messages.content = '服务器错误'
                        break
                    case 400:
                        messages.content = error.response.data.message
                        break
                    case 401:
                        messages.content = '请重新登录'
                        break
                    case 403:
                        messages.content = '您没有权限'
                        break
                    case 404:
                        messages.content = '获取资源错误404'
                        break
                    case 408:
                        messages.content = '服务器超时'
                        break
                    default:
                        messages.content = '网络不给力,请稍后再试~'
                }

                messages.status = error.response.status
            }

            alert(messages.content)
            return Promise.reject(error)
        }
    )
}
init()
// axios url query
const handlerParams = function (url, obj) {
    let i = 0
    let isHasParam

    if (url.indexOf('?') !== -1) isHasParam = true

    for (let o in obj) {
        if (obj[o] === undefined || obj[o] === null) {
            continue
        }

        if (i === 0 && !isHasParam) {
            // eslint-disable-next-line no-param-reassign
            url += '?' + o + '=' + obj[o]
        } else {
            // eslint-disable-next-line no-param-reassign
            url += '&' + o + '=' + obj[o]
        }
        i++
    }
    return url
}
// request JSON 格式
const baseJSON = function (method, url, params = {}, data = {}) {
    // eslint-disable-next-line no-param-reassign
    url = handlerParams(url, params)
    return axInstance({
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        },
        data: data,
        url
    })
}

// request form 格式
const baseForm = function (method, url, params, data) {
    // eslint-disable-next-line no-param-reassign
    params = params || {}
    // eslint-disable-next-line no-param-reassign
    data = data || {}
    // eslint-disable-next-line no-param-reassign
    url = handlerParams(url, params)

    return axInstance({
        method: method,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
            'Access-Control-Allow-Origin': '*'
        },
        data: JSON.stringify(data),
        url
    })
}

/**
 * 公共方法
 */

const postJSON = (url, params, data) => {
    return baseJSON('POST', url, params, data)
}

const getJSON = (url, params) => {
    return baseJSON('POST', url, params)
}

const postForm = (url, params, data) => {
    return baseForm('POST', url, params, data)
}
const downloadUrl = (url, params, data) => {
    // eslint-disable-next-line no-param-reassign
    params = params || {}
    // eslint-disable-next-line no-param-reassign
    data = data || {}
    // eslint-disable-next-line no-param-reassign
    url = handlerParams(url, params)

    return axInstance({
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        },
        data: data,
        url,
        responseType: 'blob'
    })
}
const downloadGETUrl = (url, params, data) => {
    // eslint-disable-next-line no-param-reassign
    params = params || {}
    // eslint-disable-next-line no-param-reassign
    data = data || {}
    // eslint-disable-next-line no-param-reassign
    url = handlerParams(url, params)

    return axInstance({
        method: 'GET',
        data: data,
        url,
        responseType: 'blob'
    })
}
const uploadFile = (_url, _data) => {}


const _GET = (function () {
    let url = window.document.location.href.toString()
    let u = url.split("?")
    if (typeof (u[1]) == "string") {
        u = u[1].split("&")
        let get = {}
        for (let i in u) {
            let j = u[i].split("=")
            get[j[0]] = j[1]
        }
        return get
    } else {
        return ''
    }
});