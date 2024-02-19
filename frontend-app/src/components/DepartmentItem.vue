<template>
    <div style="background-color: #ececec; margin: 20px; padding: 2px" v-if="department">
        <a-card :title="department.name" :bordered="false">
            <p>{{ department.note }}</p>
        </a-card>
    </div>
</template>

<script lang="ts" setup>

import {DepartmentModel} from "@/appModel";

const props = defineProps({
    department: {
        type: Object as () => DepartmentModel | undefined,
    }
});

</script>