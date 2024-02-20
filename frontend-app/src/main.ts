import {createApp} from "vue";
import App from "./App.vue";
import {
    Card,
    Row,
    Col,
    Tree,
    List,
    Button,
    Space,
    Modal,
    Form,
    Input,
    Alert,
    Select,
    Radio,
} from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';

createApp(App)
    .use(Card)
    .use(Row)
    .use(Col)
    .use(Tree)
    .use(Select)
    .use(List)
    .use(Button)
    .use(Space)
    .use(Modal)
    .use(Form)
    .use(Input)
    .use(Alert)
    .use(Radio)
    .mount("#app");
