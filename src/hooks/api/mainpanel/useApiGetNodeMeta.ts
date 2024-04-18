import { api } from "../../../api/index";
import { useQuery } from "@tanstack/vue-query";

type clusterNodeProps = {
  [index: string]: string[];
};

export const getMetaData = async (
  clusterName: string,
  address: string = "/",
  meta: boolean = true,
) => {
  const res = await api.get(
    `/api/zkui/clusters/${clusterName}/node?path=${address}&meta=${meta}`,
  );
  return res.data as clusterNodeProps;
};
export const useApiGetNodeMeta = () => {
  return (clusterName: string, address: string, meta: boolean) =>
    useQuery({
      queryKey: [clusterName, address, meta],
      queryFn: () => getMetaData(clusterName, address, meta),
    });
};
