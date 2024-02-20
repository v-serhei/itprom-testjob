import {DepartmentModel} from "@/appModel";

export interface TreeNode {
    title: string;
    key: number;
    children?: TreeNode[];
}

export interface SelectedNodeInfo {
    event: string,
    selected: boolean,
    node: TreeNode,
    selectedNodes: []
}
export const buildTree = (departments: DepartmentModel[]): TreeNode[] => {
    const departmentMap: Record<number, TreeNode> = {};
    departments.forEach(department => {
        departmentMap[department.id!] = {
            title: department.name,
            key: department.id!,
            children: []
        };
    });

    departments.forEach(department => {
        if (department.parentDepartmentId !== null) {
            // @ts-ignore
            const parentDepartment = departmentMap[department.parentDepartmentId];
            if (parentDepartment?.children) {
                parentDepartment.children.push(departmentMap[department.id!]);
            }
        }
    });

    return departments
        .filter(department => department.parentDepartmentId === null)
        .map(rootDepartment => departmentMap[rootDepartment.id!]);
};