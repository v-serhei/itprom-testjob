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
                            :employee-list="employeeList"
                            :profession-list="professionList"
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
    employeeList: {
        type: Object as () => Array<EmployeeModel>,
        required: true
    },
    professionList: {
        type: Object as () => Array<ProfessionModel>,
    },
    departmentList: {
        type: Object as () => Array<DepartmentModel>,
    },
});

const deleteErrorMessage = ref<string>("");

const deleteEmployee = async () => {
    try {
        const response = await fetchApi.delete(`/employees/${props.employee?.id}`);
        if (response.ok) {
            const index = props.employeeList?.findIndex(item => item.id === props.employee?.id);
            if (index !== -1) {
                props.employeeList?.splice(index, 1);
            }
        } else {
            deleteErrorMessage.value = await response.text();
        }
    } catch (error: any) {
        deleteErrorMessage.value = error.message;
    }
}
</script>
