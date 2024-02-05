// router/index.ts �ļ�
import { createRouter, createWebHashHistory, RouterOptions, Router, RouteRecordRaw } from 'vue-router'
type RouteRecordRaw = typeof RouteRecordRaw
type RouterOptions = typeof RouterOptions
type Router = typeof Router
//����router��APIĬ��ʹ�������ͽ��г�ʼ�����ڲ��������Ͷ��壬���Ա����ڲ������е��������������ǿ���ʡ�Ե�
//RouterRecordRaw��·���������
const routes: RouteRecordRaw[] = [
    {
        path: '/',
        name: 'Layout',
        component: () => import("~/views/Layout.vue")
    }

]

// RouterOptions��·��ѡ������
const options: RouterOptions = {
    history: createWebHashHistory(),
    routes,
}
// Router��·�ɶ�������
const router: Router = createRouter(options)

export default router