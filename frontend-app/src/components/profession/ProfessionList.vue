<template>
    <a-list item-layout="horizontal" :data-source="professionList">
        <template #renderItem="{ item }">
            <a-list-item :key="item.id">
                <ProfessionItem
                    :profession="item"
                    :profession-list="professionList"
                />
            </a-list-item>
        </template>
    </a-list>
</template>
<script lang="ts" setup>
import {ProfessionModel} from "@/appModel";
import ProfessionItem from "@/components/profession/ProfessionItem.vue";

const props = defineProps({
    professionList: {
        type: Object as () => Array<ProfessionModel>,
        required: true
    }
});

</script>