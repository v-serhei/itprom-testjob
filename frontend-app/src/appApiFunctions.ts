import {apiRoot} from "@/clientApi";

const fetchRequest = async (path: string, config: RequestInit) => {
    return await window.fetch(`${apiRoot}${path}`, {
        ...config,
        headers: {
            ...config.headers,
        }
    });
};

const fetchApi = {
    get: async (path: string) => {
        return fetchRequest(path, {
            method: "GET",
            headers: {
                Pragma: "no-cache",
            }
        });
    },

    post: async (path: string, data: object | null) => {
        const requestConfig = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        };
        return fetchRequest(path, requestConfig);
    },

    put: async (url: string, data: object | null) => {
        const requestConfig = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        };
        return fetchRequest(url, requestConfig);
    },

    delete: async (path: string) => {
        return fetchRequest(path, {
            method: "DELETE",
            headers: {
                Pragma: "no-cache",
            }
        });
    },
};

export default fetchApi;
