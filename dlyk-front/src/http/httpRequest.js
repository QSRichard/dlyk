import axios from "axios";
import {getTokenName, messageConfirm, messageTip} from "../utils/message.js";

// 定义后端接口地址前缀
axios.defaults.baseURL = 'http://localhost:8089'

export function doGet(url, params) {
    return axios(
        {
            method: "get",
            url: url,
            params: params,
            dataType: "json"
        }
    )
}

export function doDelete(url, params) {
    return axios(
        {
            method: "delete",
            url: url,
            params: params,
            dataType: "json"
        }
    ).then(
        function () {

        }
    )
}

export function doPost(url, data) {
    return axios(
        {
            method: "post",
            url: url,
            data: data,
            dataType: "json"
        }
    )
}

export function doPut(url, data) {
    return axios(
        {
            method: "put",
            url: url,
            data: data,
            dataType: "json"
        }
    ).then(
        function () {

        }
    )
}

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    let token = window.sessionStorage.getItem(getTokenName());

    if (!token) {
        // console.log('empty token');
        token = window.localStorage.getItem(getTokenName());
    }
    if (token) {
        console.log('token', token);
        config.headers['Authorization'] = token;
        config.headers['rememberMe'] = 'true';
    }
    return config;
}, (error) => {
    return Promise.reject(error);
})


// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    // 拦截token验证的结果 token问题
    if (response.data.code > 900) {
        messageConfirm(response.data.msg + '是否重新登录?').then(() => {
            // 用户点击确定 跳转登录页
            window.location.href = '/';
        }).catch(() => {
            messageTip("取消去登录", 'warning')
        })
        return;
    }
    return response;
}, (error) => {
    return Promise.reject(error);
})