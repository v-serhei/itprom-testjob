<template>
    <a-list item-layout="horizontal" :data-source="employeeList">
        <template #renderItem="{ item }">
            <a-list-item>
                <EmployeeItem
                    :employee="item"
                    :employee-list="employeeList"
                    :department-list="departmentList"
                    :profession-list="professionList"
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



</script>