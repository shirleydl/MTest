import installElementPlus from './plugins/element'
import {createApp} from 'vue'
import App from './App.vue'
import store from './store'
import './assets/css/icon.css'
import router from './router'


const app = createApp(App)
installElementPlus(app)
app
    .use(store)
    .use(router)
    .mount('#app')

