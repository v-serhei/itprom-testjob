<template>
    <div style="background-color: #ececec; margin: 20px; padding: 2px" v-if="department">
        <a-card :title="department.name" :bordered="false">
            <p>{{ department.note }}</p>
        </a-card>
        <div :style="{padding: '8px', background: 'rgb(190, 200, 200)'}">
            <a-space>
                <DepartmentEditWindow
                    :open-modal="false"
                    :department="department"
                    :department-list="departmentList"
                    :update-department="updateDepartment"
                />
                <a-button danger @click="deleteDepartment">Delete</a-button>
            </a-space>
        </div>
        <a-alert v-if="deleteErrorMessage" :message="deleteErrorMessage" type="error" show-icon/>

    </div>
</template>

<script lang="ts" setup>

import {DepartmentModel} from "@/appModel";
import {ref} from "vue";
import fetchApi from "@/appApiFunctions";
import DepartmentEditWindow from "@/components/department/DepartmentEditWindow.vue";

const props = defineProps({
    department: {
        type: Object as () => DepartmentModel | undefined,
    },
    departmentList: {
        type: Object as () => Array<DepartmentModel>,
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
const updateDepartment = (updatedDepartment: DepartmentModel) => {
    props.updateItem(props.department!.id, updatedDepartment);
};

const deleteDepartment = async () => {
    try {
        const response = await fetchApi.delete(`/departments/${props.department?.id}`);
        if (response.ok) {
            props.deleteItem(props.department!.id);
        } else {
            deleteErrorMessage.value = await response.text();
        }
    } catch (error: any) {
        deleteErrorMessage.value = error.message;
    }
}
</script>