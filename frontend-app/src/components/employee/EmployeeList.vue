<template>
    <a-list item-layout="horizontal" :data-source="employeeList">
        <template #renderItem="{ item }">
            <a-list-item>
                <EmployeeItem
                    :employee="item"
                    :department-list="departmentList"
                    :profession-list="professionList"
                    :update-item="updateEmployee"
                    :delete-item="deleteEmployee"
                />
            </a-list-item>
        </template>
    </a-list>
</template>
<script lang="ts" setup>
import {DepartmentModel, EmployeeModel, ProfessionModel} from "@/appModel";
import EmployeeItem from "@/components/employee/EmployeeItem.vue";

const props = defineProps({
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

const updateEmployee = (employeeId: number, employee: EmployeeModel): void => {
    const index = props.employeeList.findIndex(item => item.id === employeeId);
    if (index !== -1) {
        props.employeeList[index] = employee;
    }
};
const deleteEmployee = (employeeId: number): void => {
    const index = props.employeeList?.findIndex(item => item.id === employeeId);
    if (index !== -1) {
        props.employeeList?.splice(index, 1);
    }
};

</script>