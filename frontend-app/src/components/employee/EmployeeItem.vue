<template>
    <div style="background-color: #ececec; padding: 2px;">
        <a-row :gutter="16">
            <a-col>
                <a-card
                    :title="`${employee.lastName} ${employee.firstName} ${employee.middleName ? employee.middleName : ''}`"
                    :bordered="false">
                    <p>Profession:&nbsp{{ employee.profession.name }}</p>
                    <p>Department: {{ employee.department.name }}</p>
                    <p>Note: {{ employee.note }}</p>
                </a-card>
                <div :style="{padding: '8px', background: 'rgb(190, 200, 200)'}">
                    <a-space>
                        <EmployeeEditWindow
                            :open-modal="false"
                            :employee="employee"
                            :department-list="departmentList"
                            :profession-list="professionList"
                            :update-employee="updateEmployee"
                        />
                        <a-button danger @click="deleteEmployee">Delete</a-button>
                    </a-space>
                </div>
                <a-alert v-if="deleteErrorMessage" :message="deleteErrorMessage" type="error" show-icon/>
            </a-col>
        </a-row>
    </div>
</template>

<script lang="ts" setup>

import {DepartmentModel, EmployeeModel, ProfessionModel} from "@/appModel";
import EmployeeEditWindow from "@/components/employee/EmployeeEditWindow.vue";
import fetchApi from "@/appApiFunctions";
import {ref} from "vue";

const props = defineProps({
    employee: {
        type: Object as () => EmployeeModel,
        required: true
    },

    professionList: {
        type: Object as () => Array<ProfessionModel>,
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

const updateEmployee = (updatedEmployee: EmployeeModel) => {
    props.updateItem(props.employee.id, updatedEmployee);
};

const deleteEmployee = async () => {
    try {
        const response = await fetchApi.delete(`/employees/${props.employee?.id}`);
        if (response.ok) {
            props.deleteItem(props.employee.id);
        } else {
            deleteErrorMessage.value = await response.text();
        }
    } catch (error: any) {
        deleteErrorMessage.value = error.message;
    }
}

</script>
