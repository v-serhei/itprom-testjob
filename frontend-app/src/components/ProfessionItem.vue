<template>
    <div style="background-color: #ececec; padding: 2px;">
        <a-row :gutter="16">
            <a-col>
                <a-card :title="profession.name" :bordered="false">
                    <p>{{ profession.note }}</p>
                </a-card>
            </a-col>
        </a-row>
    </div>
</template>

<script lang="ts" setup>

import {ProfessionModel} from "@/appModel";

const props = defineProps({
    profession: {
        type: Object as () => ProfessionModel,
        required: true
    }
});

</script>
