<template>
    <div>
        <div class="header">
            <h1>Test application</h1>
        </div>
        <div class="main">
            <div class="content">
                <ContentContainer />
            </div>
        </div>
    </div>
</template>


<script lang="ts">
import {defineComponent} from "vue";
import ContentContainer from "@/components/ContentContainer.vue";

export default defineComponent({
    components: {
        ContentContainer,
    },

    methods: {
    }

})
</script>


<style>
body {
    margin: 0;
    padding: 0;
    font-family: Arial, sans-serif;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #2c3e50;
}

.container {
    width: 80%;
    height: 95vh;
    max-height: 100vh;
    margin: 20px;
    display: flex;
    flex-direction: column;
    background-color: #ffffff;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.header {
    height: 40px;
    width: 100%;
    background-color: #007bff;
    color: #ffffff;
    display: flex;
    align-items: center;
    padding-top: 20px;
}

.main {
    display: flex;
    flex-direction: row;
}

.content {
    flex: 1;
    padding: 20px;
    overflow: hidden;
}
</style>