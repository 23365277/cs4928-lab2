package com.github.packageurl;

import com.github.packageurl.PackageURL.StandardTypes;
import com.github.packageurl.internal.StringUtil;

final class PackageNamePolicies {
    static final PackageNamePolicy PRESERVE = value -> value;
    static final PackageNamePolicy LOWERCASE = value -> StringUtil.toLowerCase(value);
    static final PackageNamePolicy PUB =
            value -> StringUtil.toLowerCase(value).replaceAll("[^a-z0-9_]", "_");
    static final PackageNamePolicy PYPI =
            value -> StringUtil.toLowerCase(value).replace('_', '-');

    private PackageNamePolicies() {}

    static PackageNamePolicy forType(final String type) {
        switch (type) {
            case StandardTypes.APK:
            case StandardTypes.BITBUCKET:
            case StandardTypes.BITNAMI:
            case StandardTypes.COMPOSER:
            case StandardTypes.DEB:
            case StandardTypes.GITHUB:
            case StandardTypes.GOLANG:
            case StandardTypes.HEX:
            case StandardTypes.LUAROCKS:
            case StandardTypes.OCI:
                return LOWERCASE;
            case StandardTypes.PUB:
                return PUB;
            case StandardTypes.PYPI:
                return PYPI;
            default:
                return PRESERVE;
        }
    }
}
