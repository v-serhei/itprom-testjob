<template>
    <div class="departmentsContainer">
        <a-tree
            :tree-data="treeData"
            @select="setSelectedDepartment"
        >
        </a-tree>
        <div v-if="selectedKey">
            <DepartmentDetails
                :department="selectedDepartment"
                :department-list="departmentList"
            />
        </div>
    </div>

</template>

<script lang="ts" setup>
import {computed, ComputedRef, defineProps, ref, watch} from 'vue';
import {DepartmentModel} from "@/appModel";
import DepartmentDetails from "@/components/department/DepartmentItem.vue";
import {buildTree, SelectedNodeInfo, TreeNode} from "@/components/department/treeFunctions";

const props = defineProps({
    departmentList: {
        type: Object as () => Array<DepartmentModel>,
        required: true
    }
});
const selectedKey = ref<number | null>(null);
const treeData = ref<TreeNode[]>([]);

const selectedDepartment: ComputedRef<DepartmentModel | undefined> = computed(() => {
    const id = selectedKey.value;
    return props.departmentList.find((item: DepartmentModel) => item.id === id);
});

const setSelectedDepartment = (selectedKeys: Array<string>, info: SelectedNodeInfo) => {
    selectedKey.value = info.node?.key;
}

watch(props, () => {
    treeData.value = buildTree(props.departmentList);
}, {immediate: true});

</script>

<style>
.departmentsContainer {
    display: flex;
    flex-direction: row;
}
</style>
