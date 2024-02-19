<template>
    <a-list item-layout="horizontal" :data-source="professionList">
        <template #renderItem="{ item }">
            <a-list-item>
                <ProfessionItem :profession="item"/>
            </a-list-item>
        </template>
    </a-list>
</template>
<script lang="ts" setup>
import {ProfessionModel} from "@/appModel";
import ProfessionItem from "@/components/ProfessionItem.vue";

const props = defineProps({
    professionList: {
        type: Object as () => Array<ProfessionModel>,
        required: true
    }
});

</script>