


import request from "../../requests/request.ts";
//获取所有公告
export const getPublicNotice = async () => {
    return request.get("/home-page/get-all-notice").then((res) => {
        return res.data
    })
}

/**
 * 新增公告
 */
export const addPublicNotice = async (data: any) => {
    return request.post("/home-page/add-notice", data).then((res) => {
        return res.data
    })
}

/**
 * 修改公告
 */
export const updatePublicNotice = async (data: any) => {
    return request.post("/home-page/update-notice", data).then((res) => {
        return res.data
    })
}

/**
 * 删除公告
 */
export const deletePublicNotice = async (pubId: number) => {
    return request.get("/home-page/delete-notice/"+ pubId).then((res) => {
        return res.data
    })
}
