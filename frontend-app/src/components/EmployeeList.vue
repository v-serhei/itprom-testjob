<template>
    <a-list item-layout="horizontal" :data-source="employeeList">
        <template #renderItem="{ item }">
            <a-list-item>
                <EmployeeItem :employee="item"/>
            </a-list-item>
        </template>
    </a-list>
</template>
<script lang="ts" setup>
import {EmployeeModel} from "@/appModel";
import EmployeeItem from "@/components/EmployeeItem.vue";

const props = defineProps({
    employeeList: {
        type: Object as () => Array<EmployeeModel>,
        required: true
    }
});


</script>