import {api} from "../../../api/index";
import {useQuery} from "@tanstack/vue-query";

type clusterTreeProps = {
    [index: string]: string[];
}

export const useApiGetClusterTree = (DYNAMIC_QUERY_KEY: string, searchQuery: string) => {
    console.log(DYNAMIC_QUERY_KEY, searchQuery);
    const getClusterTree = async (clusterName: string) => {
        const res = await api.get(`/api/zkui/clusters/${clusterName}/tree?path=${searchQuery}`);
        return res.data as clusterTreeProps;
    }

    return useQuery({
        queryKey: [DYNAMIC_QUERY_KEY, searchQuery],
        queryFn: () => getClusterTree(DYNAMIC_QUERY_KEY)
    })
}
