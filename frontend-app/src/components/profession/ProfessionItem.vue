<template>
    <div style="background-color: #ececec; padding: 2px;">
        <a-row :gutter="16">
            <a-col>
                <a-card :title="profession.name" :bordered="false">
                    <p>{{ profession.note }}</p>
                </a-card>
                <div :style="{padding: '8px', background: 'rgb(190, 200, 200)'}">
                    <a-space>
                        <Profession-edit-window
                            :open-modal="false"
                            :profession="profession"
                            :update-profession="updateProfession"
                        />
                        <a-button danger @click="deleteProfession">Delete</a-button>
                    </a-space>
                </div>
                <a-alert v-if="deleteErrorMessage" :message="deleteErrorMessage" type="error" show-icon/>
            </a-col>
        </a-row>
    </div>
</template>

<script lang="ts" setup>

import {ProfessionModel} from "@/appModel";
import ProfessionEditWindow from "@/components/profession/ProfessionEditWindow.vue";
import fetchApi from "@/appApiFunctions";
import {ref} from "vue";

const props = defineProps({
    profession: {
        type: Object as () => ProfessionModel,
        required: true
    },

    updateItem: {
        type: Function,
        required: true
    },

    deleteItem: {
        type: Function,
        required: true
    }
});

const deleteErrorMessage = ref<string>("");
const updateProfession = (updatedProfession: ProfessionModel) => {
    props.updateItem(props.profession.id, updatedProfession);
};

const deleteProfession = async () => {
    try {
        const response = await fetchApi.delete(`/professions/${props.profession?.id}`);
        if (response.ok) {
            props.deleteItem(props.profession.id);
        } else {
            deleteErrorMessage.value = await response.text();
        }
    } catch (error: any) {
        deleteErrorMessage.value = error.message;
    }
}

</script>
