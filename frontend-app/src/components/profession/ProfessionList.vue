<template>
    <a-list item-layout="horizontal" :data-source="professionList">
        <template #renderItem="{ item }">
            <a-list-item :key="item.id">
                <ProfessionItem
                    :profession="item"
                    :update-item="updateProfession"
                    :delete-item="deleteProfession"
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

const updateProfession = (professionId: number, profession: ProfessionModel): void => {
    const index = props.professionList.findIndex(item => item.id === professionId);
    if (index !== -1) {
        props.professionList[index] = profession;
    }
};
const deleteProfession = (professionId: number): void => {
    const index = props.professionList?.findIndex(item => item.id === professionId);
    if (index !== -1) {
        props.professionList?.splice(index, 1);
    }
};

</script>