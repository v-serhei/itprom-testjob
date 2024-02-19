import {createApp} from "vue";
import App from "./App.vue";
import {Card, Row, Col, Tree, List} from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';

createApp(App)
    .use(Card)
    .use(Row)
    .use(Col)
    .use(Tree)
    .use(List)
    .mount("#app");
