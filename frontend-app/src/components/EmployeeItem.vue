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
            </a-col>
        </a-row>
    </div>
</template>

<script lang="ts" setup>

import {EmployeeModel} from "@/appModel";

const props = defineProps({
    employee: {
        type: Object as () => EmployeeModel,
        required: true
    }
});

</script>
