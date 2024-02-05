import { createApp } from "vue";
import App from "./App.vue";
import "./styles/index.scss";
import "uno.css";
import "element-plus/theme-chalk/src/message.scss";
import router from "~/router";

const app = createApp(App);
app.use(router)
app.mount("#app");
