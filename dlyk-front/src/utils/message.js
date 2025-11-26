import {ElMessage} from "element-plus";

export function messageTip(msg, type) {
    ElMessage({
        showClose: true, // 是否可以关闭消息提示框
        duration: 3000,
        message: msg, // 消息内容
        type: type, // 消息类型
    })
}

// 删除token
export function removeToken() {
    window.localStorage.removeItem("dlyk_token");
    window.sessionStorage.removeItem("dlyk_token");
}