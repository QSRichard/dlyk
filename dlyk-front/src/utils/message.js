import {ElMessage, ElMessageBox} from "element-plus";

export function messageTip(msg, type) {
    ElMessage({
        showClose: true, // 是否可以关闭消息提示框
        duration: 3000,
        message: msg, // 消息内容
        type: type, // 消息类型
    })
}


export function getTokenName() {
    return "dlyk_token";
}

// 删除token
export function removeToken() {
    window.localStorage.removeItem(getTokenName());
    window.sessionStorage.removeItem(getTokenName());
}


// 消息确认
export function messageConfirm(msg) {
    return ElMessageBox.confirm(
        msg, 'Warning',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
}