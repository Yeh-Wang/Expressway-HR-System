/// <reference types="vite/client" />

export interface ImportMetaEnv {
    readonly VITE_TENCOS_APP_ID: string;
    readonly VITE_TENCOS_APP_KEY: string;
}

export interface ImportMeta {
    readonly env: ImportMetaEnv;
}

declare module '*.vue' {
    import type { DefineComponent } from 'vue'
    // eslint-disable-next-line @typescript-eslint/no-explicit-any, @typescript-eslint/ban-types
    const component: DefineComponent<{}, {}, any>
    export default component
}

import 'vue-router'

export {}

declare module 'vue-router' {
    interface RouteMeta {
        // is optional
        requiresAuth?: boolean
        // is optional
        roles?: string[]
    }
}
