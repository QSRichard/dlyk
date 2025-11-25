import axios from "axios";

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
    axios(
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
    axios(
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