import { createApp } from "vue";
import App from "./App.vue";
import "./styles/index.scss";
import "uno.css";
import "element-plus/theme-chalk/src/message.scss";
import router from "~/router";
import Dialog from "~/chillrain_components/Dialog.vue";

const app = createApp(App);
app.use(router)
app.component("Dialog", Dialog)
app.mount("#app");
