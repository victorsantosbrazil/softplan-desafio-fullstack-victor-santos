const {
  override,
  addBabelPlugins,
  addBabelPresets,
} = require("customize-cra");

module.exports = override( 
  (config, _) => {
    config.ignoreWarnings = [/Failed to parse source map/];
    return config;
  },
  ...addBabelPresets('@babel/preset-env'),
  ...addBabelPlugins(
      "babel-plugin-transform-typescript-metadata", 
  ),
);